import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UtcConfigComponent } from './utc-config.component';

describe('UtcConfigComponent', () => {
  let component: UtcConfigComponent;
  let fixture: ComponentFixture<UtcConfigComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UtcConfigComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UtcConfigComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
