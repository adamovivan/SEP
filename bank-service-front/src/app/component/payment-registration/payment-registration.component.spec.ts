import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PaymentRegistrationComponent } from './payment-registration.component';

describe('PaymentRegistrationComponent', () => {
  let component: PaymentRegistrationComponent;
  let fixture: ComponentFixture<PaymentRegistrationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PaymentRegistrationComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PaymentRegistrationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
