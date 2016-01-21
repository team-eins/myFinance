import {bind}    from 'angular2/core';
import {bootstrap}    from 'angular2/platform/browser';
import {ROUTER_PROVIDERS, LocationStrategy, HashLocationStrategy} from 'angular2/router';
import {AppComponent} from './components/app.component.ts';

bootstrap(AppComponent, [
    ROUTER_PROVIDERS,
    bind(LocationStrategy).toClass(HashLocationStrategy)
]);