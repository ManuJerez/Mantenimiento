import http from 'k6/http';
import { sleep, check } from 'k6';

export const options = {
    stages: [
        { duration: '3m', target: 4329 }, // 100% - 5411 /////// 100% - 7215 --> 80% - 5772
        { duration: '3m', target: 4329 },
        { duration: '2m', target: 0 },
    ],
    thresholds: {
        http_req_failed: ['rate<0.01'], 
        http_req_duration: ['avg<1000'], 
    },
    abortOnFail: true, 
};

export default function () {
    const url = "http://localhost:8080/medico/1";
    const res = http.get(url);

    check(res, {
        'status was 200': (r) => r.status == 200,
    });
    sleep(1);
}