import http from 'k6/http';
import { check, sleep } from 'k6';

export const options = {
  vus: 50,
  duration: '10s',
};

export default function () {
  const res = http.get('http://localhost:8080/api/meals/weather?restaurantId=1&date=2025-04-09');
  check(res, {
    'status é 200': (r) => r.status === 200,
  });
  sleep(1);
}
