import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PrelevementResultatComponent } from './prelevement-resultat.component';

describe('PrelevementResultatComponent', () => {
  let component: PrelevementResultatComponent;
  let fixture: ComponentFixture<PrelevementResultatComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PrelevementResultatComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PrelevementResultatComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
