import {Component} from 'angular2/core';
import {NgForm} from 'angular2/common';
import {Credentials} from "../model/credentials";
import {UserService} from "../services/user.service";

@Component({
    selector: 'register',
    templateUrl: 'views/register.component.html'
})
export class RegisterComponent {

    constructor(private userService:UserService) {
    }

    pwInputType = 'password';
    model:Credentials = new Credentials('', '');

    onSubmit() {
        this.userService.register(this.model).catch((error) => console.log(error));
    }

    changePwInputType(visible:boolean) {
        visible ? this.pwInputType = 'text' : this.pwInputType = 'password';
    }
}