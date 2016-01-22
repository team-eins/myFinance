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

                this.http.post('/api/users/login', JSON.stringify({
                    username: creds.username,
                    hash: this.getHashCode(creds)
                })).subscribe(
                    response => {

                        console.log(response);

                        var data = response.json();
                        console.log(data);

                        var token = data.token;
                        if (token) {
                            localStorage.setItem("id_token", token);
                            resolve();
                        }
                        else {
                            reject("Did not receive token.");

                            // Todo: remove
                            console.log(response);
                        }
                    },
                    err => {
                        // Todo: remove
                        console.log(err);

                        reject('Data not available');
                    });
            }
        );
    };

    public register:(creds:Credentials) => Promise < any > = function (creds:Credentials) {
        return new Promise((resolve, reject) => {

            this.http.post('/api/users/register', JSON.stringify({
                username: creds.username,
                hash: this.getHashCode(creds)
            })).subscribe(
                response => {

                    // Try to login
                    this.login(creds).then(resolve).catch(reject);
                },
                err => reject(err)
            );
        });
    };

    private getHashCode:(creds:Credentials) => string = function (creds:Credentials) {

        return CryptoJS.SHA256(creds.username + creds.password).toString(CryptoJS.enc.Base64);
    };
}