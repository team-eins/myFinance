import {Component} from 'angular2/core';
import {NgForm} from 'angular2/common';
import {Credentials} from "../model/credentials";

@Component({
    selector: 'login',
    templateUrl: 'views/login.component.html'
})
export class LoginComponent {


    model:Credentials = new Credentials('', '');

    submitted = false;

    onSubmit() {
        console.log("Submitted " + this.model.username + ' ' + this.model.password);
        this.submitted = true;
    }
}