import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { ActivatedRoute } from '@angular/router';
import { AirDropService } from '../../entities/air-drop/air-drop.service';
import { AirDrop } from '../../entities/air-drop/air-drop.model';
import { ITEMS_PER_PAGE } from '../../shared';
import { JhiParseLinks, JhiAlertService } from 'ng-jhipster';

@Component({
  selector: 'jhi-airdrops-home',
  templateUrl: './airdrops-home.component.html',
  styles: []
})
export class AirdropsHomeComponent implements OnInit {
    airDrops: AirDrop[];
    currentAccount: any;
    itemsPerPage: number;
    links: any;
    page: any;
    predicate: any;
    queryCount: any;
    reverse: any;
    totalItems: number;
    currentSearch: string;

    constructor(
      private airDropService: AirDropService,
      private parseLinks: JhiParseLinks,
      private activatedRoute: ActivatedRoute,
      private jhiAlertService: JhiAlertService,
  ) {
      this.airDrops = [];
      this.itemsPerPage = ITEMS_PER_PAGE;
      this.page = 0;
      this.links = {
          last: 0
      };
      this.predicate = 'id';
      this.reverse = true;
      this.currentSearch = this.activatedRoute.snapshot && this.activatedRoute.snapshot.params['search'] ?
          this.activatedRoute.snapshot.params['search'] : '';
  }

  ngOnInit() {
  }

    loadAll() {
        if (this.currentSearch) {
            this.airDropService.search({
                query: this.currentSearch,
                page: this.page,
                size: this.itemsPerPage,
                sort: this.sort()
            }).subscribe(
                (res: HttpResponse<AirDrop[]>) => this.onSuccess(res.body, res.headers),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
            return;
        }
        this.airDropService.query({
            page: this.page,
            size: this.itemsPerPage,
            sort: this.sort()
        }).subscribe(
            (res: HttpResponse<AirDrop[]>) => this.onSuccess(res.body, res.headers),
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    trackId(index: number, item: AirDrop) {
        return item.id;
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    reset() {
        this.page = 0;
        this.airDrops = [];
        this.loadAll();
    }

    loadPage(page) {
        this.page = page;
        this.loadAll();
    }

    clear() {
        this.airDrops = [];
        this.links = {
            last: 0
        };
        this.page = 0;
        this.predicate = 'id';
        this.reverse = true;
        this.currentSearch = '';
        this.loadAll();
    }

    private onSuccess(data, headers) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = headers.get('X-Total-Count');
        for (let i = 0; i < data.length; i++) {
            this.airDrops.push(data[i]);
        }
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }

}
