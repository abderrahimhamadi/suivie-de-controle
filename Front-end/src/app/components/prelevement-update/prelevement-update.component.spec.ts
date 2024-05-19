import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PrelevementUpdateComponent } from './prelevement-update.component';

describe('PrelevementUpdateComponent', () => {
  let component: PrelevementUpdateComponent;
  let fixture: ComponentFixture<PrelevementUpdateComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PrelevementUpdateComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PrelevementUpdateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
