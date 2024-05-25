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
        sleep(5);

        page.locator('input[name="nombre"]').type('Jose');
        page.locator('input[name="DNI"]').type('445');

        const loginButton = page.locator('button[name=login]');

        sleep(2);
        await Promise.all([page.waitForNavigation(), loginButton.click()]);

        check(page, {
            'header': p => p.locator('h2').textContent() == 'Listado de pacientes',
        });
    } finally {
        page.close();
    }
}