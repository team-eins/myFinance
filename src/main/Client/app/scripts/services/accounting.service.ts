import {Injectable} from 'angular2/core';
import {AuthHttp} from 'angular2-jwt';
import {Account} from "../model/account";
import {Credentials} from "../model/credentials";
import {Transaction} from "../model/transaction";
import {Http} from "angular2/http";
import {JwtHelper} from 'angular2-jwt';


@Injectable()
export class AccountingService {

    private demoAccounts:Array<Account> = [
        {
            "id": 1,
            "name": "Versicherungen",
            "transactionCount": 0,
            "balance": 0.0,
            "lastMonth": 0.0
        }, {
            id: 2,
            name: 'Einkünfte',
            transactionCount: 0,
            balance: 0.0,
            lastMonth: 0.0,
        }, {
            id: 3,
            name: 'Hobbies',
            transactionCount: 0,
            balance: 0.0,
            lastMonth: 0.0,
        }
    ];
    private jwtHelper = new JwtHelper();


    constructor(private ahttp:AuthHttp, private http:Http) {
    }

    public getAccounts:(cred:Credentials) => Promise<Array<Account>> = function (cred:Credentials) {
        return new Promise((resolve, reject) => {

            //this.ahttp.get('api/accounts')
            //    .subscribe(
            //        data => resolve(data),
            //        err => {
            //            console.log(err);
            //            reject(err)
            //        },
            //        () => console.log('Request Complete')
            //    );

            resolve(this.demoAccounts);

        });
    };

    public getTransactions:(account:Account) => Promise<Array<Transaction>> = function (account:Account) {
        return new Promise((reject, resolve)=> {

        });
    };
}