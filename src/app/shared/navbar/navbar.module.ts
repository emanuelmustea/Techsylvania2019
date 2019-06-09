import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { NavbarComponent } from './navbar.component';
import { FormsModule } from '@angular/forms';
import { SharedModuleModule } from 'src/app/shared-module/shared-module.module';

@NgModule({
    imports: [ RouterModule, CommonModule, FormsModule,SharedModuleModule],
    declarations: [ NavbarComponent, ],
    exports: [ NavbarComponent ]
})

export class NavbarModule {}
