import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs/internal/Observable';

@Injectable({
  providedIn: 'root',
})
export class Api {

  constructor(private http: HttpClient) {}

  getLiveStatusStream(): EventSource {
  return new EventSource("http://localhost:8081/api/livestatus");
}
  getLiveControlStream(): EventSource {
  return new EventSource("http://localhost:8081/api/livecontrol");
}

  getLiveSollStream(): EventSource {
  return new EventSource("http://localhost:8081/api/livesoll");
}

  getLiveIstStream(): EventSource {
  return new EventSource("http://localhost:8081/api/liveist");
}

  getLiveDiffStream(): EventSource {
  return new EventSource("http://localhost:8081/api/livediff");
}
  getLivePositionStream(): EventSource {
  return new EventSource("http://localhost:8081/api/liveposition");
}

  getMode(): Observable<any> {
    return this.http.get(
    "http://localhost:8081/api/mode"
    );
  }

  getDifferenzWert(): Observable<any> {
    return this.http.get(
    "http://localhost:8081/api/temperature/lst"
    );
  }

  getAllIstWert(): Observable<any> {
    return this.http.get(
    "http://localhost:8081/api/temperature/differenz/all"
    );
  }

  getAllDifferenzWert(): Observable<any> {
    return this.http.get(
    "http://localhost:8081/api/temperature/lst/all"
    );
  }

  getAllSollWert(): Observable<any> {
    return this.http.get(
    "http://localhost:8081/api/temperature/Soll/all"
    );
  }

  sendControl(mode: number) {
   return this.http.post(
  `http://localhost:8080/wago/control/${mode}`,
  null,
  { responseType: 'text' }
);
}


}
