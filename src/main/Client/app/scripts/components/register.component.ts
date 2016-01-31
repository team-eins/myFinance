import {Component} from 'angular2/core';
import {NgForm} from 'angular2/common';
import {Credentials} from "../model/credentials";
import {UserService} from "../services/user.service";
import {Router} from "angular2/router";

@Component({
    selector: 'register',
    templateUrl: 'views/register.component.html'
})
export class RegisterComponent {

    constructor(private userService:UserService, private router:Router) {
    }

    pwInputType = 'password';
    model:Credentials = new Credentials('', '');
    error:string;

    onSubmit() {
        this.userService.register(this.model)
            .then(()=> this.router.navigate(['Accounts']))
            .catch((error) => {
                this.error = error;
                console.log(error);
            });
    }

    changePwInputType(visible:boolean) {
        visible ? this.pwInputType = 'text' : this.pwInputType = 'password';
    }
}