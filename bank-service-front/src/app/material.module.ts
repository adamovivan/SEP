import { NgModule } from '@angular/core';

import {
    MatButtonModule,
    MatCardModule,
    MatToolbarModule
} from '@angular/material';


@NgModule({
    imports: [
        MatButtonModule,
        MatCardModule,
        MatToolbarModule,
        MatCardModule
    ],
    exports: [
        MatButtonModule,
        MatCardModule,
        MatToolbarModule,
        MatCardModule
    ]
})
export class MaterialModule { }
