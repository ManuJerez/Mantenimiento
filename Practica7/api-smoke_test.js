/*
AUTORES:
- MANUEL JESUS JEREZ SANCHEZ
- PABLO ASTUDILLO FRAGA
*/

import http from 'k6/http'
import { check, sleep } from 'k6';

export const options = {
    vus: 5,
    duration: '1m',
    thresholds: {
        http_req_failed: [{
            threshold: 'rate <= 0',
            abortOnFail: true
        }],
        http_req_duration: ['avg < 100'],
    },
};

export default function () {
    const url = "http://localhost:8080/medico/1";
    const res = http.get(url);

    check(res, {
        'response code was 200': (res) => res.status == 200,
    });
    sleep(1);
}