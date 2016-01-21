import {Component} from 'angular2/core';
import {NgForm} from 'angular2/common';
import {Credentials} from "../model/credentials";
import {UserService} from "../services/user.service";

@Component({
    selector: 'login',
    templateUrl: 'views/login.component.html'
})
export class LoginComponent {

    constructor(private userService:UserService){}

    model:Credentials = new Credentials('', '');

    submitted = false;

    onSubmit() {
        console.log("Submitted " + this.model.username + ' ' + this.model.password);
        this.submitted = true;

        this.userService.login(this.model).catch((error) => console.log(error));
    }
}