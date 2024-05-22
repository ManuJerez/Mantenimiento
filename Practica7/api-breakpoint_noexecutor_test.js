/*
AUTORES:
- MANUEL JESUS JEREZ SANCHEZ
- PABLO ASTUDILLO FRAGA
*/

import http from 'k6/http'

export const options = {
    stages: [
        { duration: '10m', target: 100000 },
    ],
    thresholds: {
        http_req_failed: [{
            threshold: 'rate <= 0.01',
            abortOnFail: true,
        }]
    }
};

export default function () {
    const url = "http://localhost:8080/medico/1";
    const res = http.get(url);
}