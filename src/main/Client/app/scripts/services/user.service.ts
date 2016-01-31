import {Injectable} from 'angular2/core';
import {Http} from 'angular2/http';
import {Credentials} from "../model/credentials";
import {EventEmitter} from "angular2/core";
import {Output} from "angular2/core";
var CryptoJS = require('crypto-js');


@Injectable()
export class UserService {

    logon: EventEmitter<string> = new EventEmitter();
    logoff: EventEmitter<any> = new EventEmitter();

    getLogonEmitter(){
        return this.logon;
    }

    getLogoffEmitter() {
        return this.logoff;
    }

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
                            this.logon.emit(this.getUsername());
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

    public logout:() => void = function() {
        localStorage.removeItem('id_token');
        this.logoff.emit();
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
                err => {
                    if(err.status === 404){
                        reject("Dienst nicht verfügbar");
                    } else if(err.status === 409) {
                        reject("Benutzername bereits vergeben");
                    } else {
                        reject("Unbekannter Fehler");
                    }
                }
            );
        });
    };

    private getHashCode:(creds:Credentials) => string = function (creds:Credentials) {

        return CryptoJS.SHA256(creds.username + creds.password).toString(CryptoJS.enc.Base64);
    };

    private getUsername = function(){

        var token = localStorage.getItem('id_token');

        if(token) {

            var parts = token.split('.');

            if(parts[1]){
                var uInfo = JSON.parse(atob(parts[1]));

                return uInfo.alias;
            };
        }

    }
}