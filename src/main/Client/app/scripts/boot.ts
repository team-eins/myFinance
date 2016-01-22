// Vendor
import {provide}    from 'angular2/core';
import {bootstrap}    from 'angular2/platform/browser';
import {ROUTER_PROVIDERS, LocationStrategy, HashLocationStrategy} from 'angular2/router';
import {HTTP_PROVIDERS} from 'angular2/http';
import {AuthHttp, AuthConfig} from 'angular2-jwt';

// Custom
import {AppComponent} from './components/app.component.ts';
import {UserService} from "./services/user.service";

bootstrap(AppComponent, [
    HTTP_PROVIDERS,

    // Router configuration
    ROUTER_PROVIDERS,
    provide(LocationStrategy, {useClass: HashLocationStrategy}),

    // Handler for JsonWebToken Authentication
    provide(AuthConfig, {useFactory: ()=> {return new AuthConfig()}}),
    AuthHttp,

    // Custom services
    UserService
]);