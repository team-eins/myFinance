import {Injectable} from 'angular2/core';
import {Http} from 'angular2/http';
import {Credentials} from "../model/credentials";
var CryptoJS = require('crypto-js');


@Injectable()
export class UserService {

    constructor(private http:Http) {
    }

    public login:(creds:Credentials) => Promise<any> = function (creds:Credentials) {
        return new Promise((resolve, reject) => {

            var hash = CryptoJS.SHA256(creds.password).toString(CryptoJS.enc.Base64);

            this.http.post('/api/users/login', JSON.stringify({username: creds.username, hash: hash})).subscribe(
                response => {
                    if (response.status === 200) {
                        resolve(response.json());
                    }
                    else if (response.status === 404) {
                        console.info('AreaService: Fail to load data form server. Serving demo data');
                        //resolve(this.demoAreas);
                        reject(response);
                    }
                    else {
                        reject(response);
                    }
                },
                err => reject('Data not available')
            );
        });
    };

    public register:(creds:Credentials) => Promise<any> = function (creds:Credentials) {
        return new Promise((resolve, reject) => {

            var hash = CryptoJS.SHA256(creds.password).toString(CryptoJS.enc.Base64);

            this.http.post('/api/users/register', JSON.stringify({username: creds.username, hash: hash})).subscribe(
                response => {
                    if (response.status === 200) {
                        resolve(response.json());
                    }
                    else if (response.status === 404) {
                        console.info('AreaService: Fail to load data form server. Serving demo data');
                        //resolve(this.demoAreas);
                        reject(response);
                    }
                    else {
                        reject(response);
                    }
                },
                err => reject(err)
            );
        });
    };


}