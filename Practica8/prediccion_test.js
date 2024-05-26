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

        let pacient = page.$$("table tbody tr")[0].$('td[name = "nombre"]');
        await Promise.all([page.waitForNavigation(), pacient.click()]);
        sleep(3);

        let viewImageButton = page.$$('table tbody tr')[0].$('button[name = "view"]');
        await Promise.all([page.waitForNavigation(), viewImageButton.click()]);

        sleep(3);
        let predictButton = page.$('button[name = "predict"]');
        await predictButton.click();
        page.waitForSelector('span[name="predict"]');

        //Comprobar que se ha realizado la prediccion
        check(page, {
            'prediccion': p => p.locator('span[name = "predict"').textContent().includes("Probabilidad de cáncer"),
        });
        sleep(2);
    } finally {
        page.close();
    }
} 