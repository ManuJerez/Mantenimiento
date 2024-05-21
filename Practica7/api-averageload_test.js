import http from 'k6/http';
import { sleep, check } from 'k6';

export const options = {
    stages: [
        { duration: '3m', target: 3608 }, // 100% - 7215 
        { duration: '3m', target: 3608 },
        { duration: '2m', target: 0 },
    ],
    thresholds: {
        http_req_failed: ['rate<0.01'],
    },
    abortOnFail: true,
};

export default function () {
    const url = "http://localhost:8080/medico/1";
    const res = http.get(url);

    check(res, {
        'status was 200': (r) => r.status == 200
    });
    sleep(1);
}