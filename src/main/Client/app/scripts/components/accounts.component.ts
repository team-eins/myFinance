import {Component} from 'angular2/core';
import {AccountingService} from "../services/accounting.service";
import {Credentials} from "../model/credentials";
import {Account} from "../model/account";

@Component({
    selector: 'accounts',
    templateUrl: 'views/accounts.component.html'
})
export class AccountsComponent {

    accounts:Array<Account> = [];

    constructor(accountingService:AccountingService) {
        accountingService.getAccounts(this.creds)
            .then(data => this.accounts = data)
            .catch(()=> {
                console.log('Did not get account data');
            });
    }

    creds:Credentials = {username: 'Tom Riddle', password: 'evilLord'};

}