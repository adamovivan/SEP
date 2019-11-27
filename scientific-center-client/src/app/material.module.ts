import { NgModule } from '@angular/core';
import {
  MatTableModule, 
  MatSortModule, 
  MatSelectModule,
   MatInputModule,
    MatFormFieldModule, 
    MatButtonModule, 
    MatToolbarModule, 
    MatIconModule, 
    MatMenuModule, 
    MatProgressSpinnerModule,
     MatCardModule
} from '@angular/material';
import { AppRoutingModule } from './app-routing.module';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { BrowserModule } from '@angular/platform-browser';

@NgModule({
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    AppRoutingModule,
    MatCardModule,
    MatProgressSpinnerModule,
    MatMenuModule,
    MatIconModule,
    MatToolbarModule,
    MatButtonModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatSortModule,
    MatTableModule
  ],
  exports: [                                                                                                                                                                                                                          
    BrowserAnimationsModule,    
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    AppRoutingModule,
    MatCardModule,
    MatProgressSpinnerModule,
    MatMenuModule,
    MatIconModule,
    MatToolbarModule,
    MatButtonModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatSortModule,
    MatTableModule
  ]
})
export class MaterialModule { }
