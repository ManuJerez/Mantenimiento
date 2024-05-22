import http from 'k6/http';
import { check } from 'k6';

export const options = {
    stages: [
        { duration: '3m', target: 11550 },
        { duration: '3m', target: 11550 },
        { duration: '2m', target: 0 },
    ],
    thresholds: {
        http_req_failed: [{
            threshold: 'rate<0.01',
            abortOnFail: true
        }],
        http_req_duration: ['avg<1000'],
    },
};

export default function () {
    const url = "http://localhost:8080/medico/1";
    const res = http.get(url);

    check(res, {
        'status was 200': (r) => r.status == 200,
    });
}