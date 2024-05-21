import http from 'k6/http';
import { sleep, check } from 'k6';

export const options = {
    stages: [
        { duration: '2m', target: 2886 }, // 100% - 7215
        { duration: '2m', target: 0 }, 
    ],
    thresholds: {
        http_req_failed: ['rate<0.005'], 
    },
    abortOnFail: true, 
};

export default function () {
    const url = "http://localhost:8080/medico/1";
    const res = http.get(url);

    check(res, {
        'status was 200': (r) => r.status == 200,
    });
}