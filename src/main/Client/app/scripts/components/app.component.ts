import {Component} from 'angular2/core';
import {ROUTER_DIRECTIVES, RouteConfig, RouterOutlet, RouterLink, Router} from 'angular2/router';

import {HomeComponent} from "./home.component";
import {LoginComponent} from "./login.component";
import {RegisterComponent} from "./register.component";
import {AccountsComponent} from "./accounts.component";
import {UserService} from "../services/user.service";


@Component({
    selector: 'app',
    templateUrl: 'views/app.component.html',
    directives: [RouterOutlet, RouterLink]
})
@RouteConfig([
    {path: '/', component: HomeComponent, as: 'Home'},
    {path: '/login', component: LoginComponent, as: 'Login'},
    {path: '/register', component: RegisterComponent, as: 'Register'},
    {path: '/accounts', component: AccountsComponent, as: 'Accounts'}
])
export class AppComponent {

    public title = 'myFinance';
    public username: string;

    onLogonEmitter: any;
    onLogoffEmitter: any;

    constructor(private userService: UserService, private router: Router) {

        this.onLogonEmitter = userService.getLogonEmitter().subscribe(name => this.username = name);
        this.onLogoffEmitter = userService.getLogoffEmitter().subscribe(() => this.username = undefined);
    }

    onLogout = function(){
        this.userService.logout();
        this.router.navigate(['Login']);
    }
}