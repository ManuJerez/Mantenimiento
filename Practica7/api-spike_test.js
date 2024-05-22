/*
AUTORES:
- MANUEL JESUS JEREZ SANCHEZ
- PABLO ASTUDILLO FRAGA
*/

import http from 'k6/http';
import { check } from 'k6';

export const options = {
    stages: [
        { duration: '1m', target: 5775 },
        { duration: '1m', target: 0 },
    ],
    thresholds: {
        http_req_failed: ['rate<0.005'],
    },
};

export default function () {
    const url = "http://localhost:8080/medico/1";
    const res = http.get(url);

    check(res, {
        'status was 200': (r) => r.status == 200,
    });
}