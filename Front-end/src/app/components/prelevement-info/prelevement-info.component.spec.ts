import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PrelevementInfoComponent } from './prelevement-info.component';

describe('PrelevementInfoComponent', () => {
  let component: PrelevementInfoComponent;
  let fixture: ComponentFixture<PrelevementInfoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PrelevementInfoComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PrelevementInfoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
