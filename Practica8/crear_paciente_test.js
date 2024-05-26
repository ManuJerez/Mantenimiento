/*
AUTORES:
- MANUEL JESUS JEREZ SANCHEZ
- PABLO ASTUDILLO FRAGA
*/

import { browser } from 'k6/experimental/browser';
import { check, sleep } from 'k6';

export const options = {
    scenarios: {
        ui: {
            executor: 'shared-iterations',
            options: {
                browser: {
                    type: 'chromium',
                }
            }
        }
    },
    thresholds: {
        checks: ["rate == 1.0"]
    }
}

export default async function () {
    const page = browser.newPage();
    try {
        await page.goto('http://localhost:4200');
        sleep(2);

        page.locator('input[name="nombre"]').type('PABLO');
        page.locator('input[name="DNI"]').type('111111A');

        const loginButton = page.locator('button[name=login]');
        sleep(3);
        await Promise.all([page.waitForNavigation(), loginButton.click()]);

        //Comprobar que se ha redirigido a la página de pacientes
        check(page, {
            'headerPacientes': p => p.locator('h2').textContent() == 'Listado de pacientes',
        });
        sleep(3);

        const addButton = page.locator('button[name=add]');
        sleep(3);
        await Promise.all([page.waitForNavigation(), addButton.click()]);

        //Comprobar que se ha redirigido a la página de creacion de pacientes
        check(page, {
            'creatorPacientes': p => p.locator('mat-card-title').textContent() == 'Añadir un nuevo paciente',
        });
        sleep(3);

        page.locator('input[name="dni"]').type('111111A');
        page.locator('input[name="nombre"]').type('PABLO');
        page.locator('input[name="edad"]').type('22');
        page.locator('input[name="cita"]').type('CARDIOLOGÍA');

        const createButton = page.locator('button[type=submit]');
        sleep(3);
        await Promise.all([page.waitForNavigation(), createButton.click()]);

        //Comprobar que se ha redirigido a la página de pacientes
        check(page, {
            'createdPaciente': p => p.locator('h2').textContent() == 'Listado de pacientes',
        });
        sleep(3);

        let len = page.$$("table tbody tr").length;

        //Comprobamos que se ha añadido el paciente
        check(page, {
            'pacienteAñadido': p => parseInt(p.$$("table tbody tr")[len-1]
            .$('td[name="dni"]').textContent()) == 111111,
        });
        sleep(3);
        
    } finally {
        page.close();
    }
} 