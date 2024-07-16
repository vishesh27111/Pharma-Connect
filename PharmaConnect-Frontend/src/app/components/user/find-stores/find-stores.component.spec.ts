import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FindStoresComponent } from './find-stores.component';

describe('FindStoresComponent', () => {
  let component: FindStoresComponent;
  let fixture: ComponentFixture<FindStoresComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [FindStoresComponent]
    });
    fixture = TestBed.createComponent(FindStoresComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
