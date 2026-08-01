import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Api } from '../services/api';

@Component({
  selector: 'app-values',
  imports: [CommonModule],
  templateUrl: './values.html',
  styleUrl: './values.css',
})
export class Values implements OnInit {

  ValuesIst: any[] = [];
  ValuesSoll: any[] = [];
  ValuesDifferenz: any[] = [];

  selectedType: 'ist' | 'soll' | 'diff' = 'ist';

  get selectedWerte(): any[] {
    if (this.selectedType === 'soll') return this.ValuesSoll;
    if (this.selectedType === 'diff') return this.ValuesDifferenz;
    return this.ValuesIst;
  }

  get selectedLabel(): string {
    if (this.selectedType === 'soll') return 'SOLL';
    if (this.selectedType === 'diff') return 'DIFF';
    return 'IST';
  }

  get selectedUnit(): string {
    return this.selectedType === 'diff' ? 'Δ°C' : '°C';
  }

  get selectedBadgeClass(): string {
    if (this.selectedType === 'soll') return 'badge-soll';
    if (this.selectedType === 'diff') return 'badge-diff';
    return 'badge-ist';
  }

  get selectedValueClass(): string {
    if (this.selectedType === 'soll') return 'blue';
    if (this.selectedType === 'diff') return 'orange';
    return 'green';
  }

  constructor(private cdr: ChangeDetectorRef,private api: Api) {}

  ngOnInit() {
    this.api.getAllIstWert().subscribe({
      next: (response: any[]) => { this.ValuesIst = response;
        this.cdr.detectChanges();
       },
      error: (err) => console.log(err)
    });

    this.api.getAllSollWert().subscribe({
      next: (response: any[]) => { this.ValuesSoll = response; },
      error: (err) => console.log(err)
    });

    this.api.getAllDifferenzWert().subscribe({
      next: (response: any[]) => { this.ValuesDifferenz = response; },
      error: (err) => console.log(err)
    });
  }

  pageSize = 10;
currentPage = 1;

get totalPages(): number {
  return Math.ceil(this.selectedWerte.length / this.pageSize);
}

get totalPagesArray(): number[] {
  return Array.from({ length: this.totalPages }, (_, i) => i + 1);
}

get pagedWerte(): any[] {
  const start = (this.currentPage - 1) * this.pageSize;
  return this.selectedWerte.slice(start, start + this.pageSize);
}

goToPage(page: number) {
  if (page >= 1 && page <= this.totalPages) {
    this.currentPage = page;
  }
}

selectType(type: 'ist' | 'soll' | 'diff') {
  this.selectedType = type;
  this.currentPage = 1; // reset to page 1 on filter change
}
}