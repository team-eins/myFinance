import {Component} from 'angular2/core';
import {ROUTER_DIRECTIVES, RouteConfig, RouterOutlet, RouterLink} from 'angular2/router';

import {HomeComponent} from "./home.component";
import {LoginComponent} from "./login.component";
import {RegisterComponent} from "./register.component";
import {AccountsComponent} from "./accounts.component";


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
}