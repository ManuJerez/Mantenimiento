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
        //sleep(5);

        page.locator('input[name="nombre"]').type('Jose');
        page.locator('input[name="DNI"]').type('445');

        const loginButton = page.locator('button[name=login]');

        //sleep(2);
        await Promise.all([page.waitForNavigation(), loginButton.click()]);

        check(page, {
            'headerPacientes': p => p.locator('h2').textContent() == 'Listado de pacientes',
        });

        //sleep(2);
        let pacient = page.$$("table tbody tr")[0].$('td[name = "nombre"]');
        console.log(pacient.textContent())
        await Promise.all([page.waitForNavigation(), pacient.click()]);
        sleep(2);

        let viewImageButton = page.$$('table tbody tr')[0].$('button[name = "view"]');
        //console.log(viewImageButton)
        await Promise.all([page.waitForNavigation(), viewImageButton.click()]);
        sleep(2)

        let predictButton = page.$('button[name = "predict"]');
        predictButton.click();
        page.waitForSelector('span[name="predict"]');
        //await Promise.all([page.waitForSelector('span[name = "predict"]'), predictButton.click()]);
        sleep(2)
    } finally {
        page.close();
    }
} 