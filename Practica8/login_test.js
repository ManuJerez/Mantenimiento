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

        sleep(2);
        await Promise.all([page.waitForNavigation(), loginButton.click()]);

        //Comprobar que se ha redirigido a la página de pacientes
        check(page, {
            'headerPacientes': p => p.locator('h2').textContent() == 'Listado de pacientes',
        });
        sleep(3);
    } finally {
        page.close();
    }
}