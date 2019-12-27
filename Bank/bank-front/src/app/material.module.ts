import { NgModule } from '@angular/core';

import {
    MatButtonModule,
    MatCardModule,
    MatInputModule,
    MatToolbarModule,
    MatSnackBarModule
} from '@angular/material';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material';

@NgModule({
    imports: [
        MatButtonModule,
        MatCardModule,
        ReactiveFormsModule,
        FormsModule,
        MatFormFieldModule,
        MatInputModule,
        MatToolbarModule,
        MatCardModule,
        MatSnackBarModule
    ],
    exports: [
        MatButtonModule,
        MatCardModule,
        ReactiveFormsModule,
        FormsModule,
        MatFormFieldModule,
        MatInputModule,
        MatToolbarModule,
        MatCardModule,
        MatSnackBarModule
    ]
})
export class MaterialModule { }
