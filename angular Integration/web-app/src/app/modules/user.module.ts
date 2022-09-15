import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { UserRoutingModule } from './user/user-routing.module';
import { UserDashboardComponent } from './user/components/user-dashboard/user-dashboard.component';
import { HeaderComponent } from './user/components/header/header.component';
import { FooterComponent } from './user/components/footer/footer.component';
import { HomeComponent } from './user/components/home/home.component';
import { ContactComponent } from './user/components/contact/contact.component';

@NgModule({
  declarations: [
    UserDashboardComponent,
    HeaderComponent,
    FooterComponent,
    HomeComponent,
    ContactComponent,
  ],
  imports: [CommonModule, UserRoutingModule],
})
export class UserModule {}
