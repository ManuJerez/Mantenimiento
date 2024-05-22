import http from 'k6/http';
import { sleep, check } from 'k6';

export const options = {
    stages: [
        { duration: '3m', target: 7219 }, // 100% = 7215 -> 80% = 3608
        { duration: '3m', target: 7219 },
        { duration: '2m', target: 0 },
    ],
    thresholds: {
        http_req_failed: [{
            threshold: 'rate<0.01',
            abortOnFail: true 
        }],
    },
};

export default function () {
    const url = "http://localhost:8080/medico/1";
    const res = http.get(url);

    check(res, {
        'status was 200': (r) => r.status == 200
    });
}