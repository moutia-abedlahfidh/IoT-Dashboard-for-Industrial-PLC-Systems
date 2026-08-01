import { Api } from '../services/api';
import { ChangeDetectorRef,Component, OnInit, OnDestroy, Inject, PLATFORM_ID } from '@angular/core';
import { CommonModule, isPlatformBrowser } from '@angular/common';
import Map from 'ol/Map';
import View from 'ol/View';
import TileLayer from 'ol/layer/Tile';
import OSM from 'ol/source/OSM';
import { fromLonLat, toLonLat } from 'ol/proj';
import Feature from 'ol/Feature';
import Point from 'ol/geom/Point';
import VectorLayer from 'ol/layer/Vector';
import VectorSource from 'ol/source/Vector';
import { Style, Icon } from 'ol/style';
import Overlay from 'ol/Overlay';


@Component({
  selector: 'app-position',
  imports: [],
  templateUrl: './position.html',
  styleUrl: './position.css',
})
export class Position implements OnInit, OnDestroy{
latitude:  number = 51.44706;
  longitude: number = 7.27074;
  altitude:  any = -17.0;
private map!: Map;
  private markerSource = new VectorSource();
  private positionSource?: EventSource;
  private watchId!: number;
  constructor(private cdr: ChangeDetectorRef,private api : Api,@Inject(PLATFORM_ID) private platformId: Object) {}
  ngOnInit() {
    

    if (isPlatformBrowser(this.platformId)) {
      this.LiveSoll();
      this.initMap();
    }
  }

  LiveSoll(): void {
  const eventSourceControl = this.api.getLivePositionStream();

  eventSourceControl.onmessage = (event) => {
    const latitudeMatch = event.data.match(/latitude=(-?[\d.]+)/);
    const longtitudeMatch = event.data.match(/longtitude=(-?[\d.]+)/);
    const altitudeMatch = event.data.match(/altitude=(-?[\d.]+)/);

    this.latitude = latitudeMatch ? Number(latitudeMatch[1]) : 0.0;
    this.longitude = longtitudeMatch ? Number(longtitudeMatch[1]) : 0.0;
    this.altitude = altitudeMatch ? Number(altitudeMatch[1]) : 0.0;

    this.map.getView().setCenter(fromLonLat([this.longitude, this.latitude]));
    this.map.getView().setZoom(15);
    this.placeMarker(this.longitude, this.latitude);

    this.cdr.detectChanges();
  };

  eventSourceControl.onerror = (error) => {
    console.error("Stream error:", error);
  };
} 

ngOnDestroy(): void {
    this.positionSource?.close();

    if (this.watchId !== undefined && isPlatformBrowser(this.platformId)) {
    navigator.geolocation.clearWatch(this.watchId);
    }

  this.map?.setTarget(undefined);
  }

  private initMap(): void {
  const markerLayer = new VectorLayer({
    source: this.markerSource,
    style: new Style({
      image: new Icon({
        src: 'https://cdn-icons-png.flaticon.com/512/684/684908.png',
        scale: 0.06,
        anchor: [0.5, 1]
      })
    })
  });

  this.map = new Map({
    target: 'map',
    layers: [
      new TileLayer({ source: new OSM() }),
      markerLayer
    ],
    view: new View({
      center: fromLonLat([this.longitude, this.latitude]),
      zoom: 13
    })
  });

  this.placeMarker(this.longitude, this.latitude);
}

  private trackLocation(): void {
    if (!navigator.geolocation) return;

    this.watchId = navigator.geolocation.watchPosition(
      (pos) => {
        this.latitude  = pos.coords.latitude;
        this.longitude = pos.coords.longitude;
        this.altitude  = pos.coords.altitude;

        this.map.getView().setCenter(fromLonLat([this.longitude, this.latitude]));
        this.map.getView().setZoom(15);
        this.placeMarker(this.longitude, this.latitude);
      },
      (err) => console.warn('Geolocation error:', err),
      { enableHighAccuracy: true }
    );
  }

  private placeMarker(lon: number, lat: number): void {
    this.markerSource.clear();
    const feature = new Feature({
      geometry: new Point(fromLonLat([lon, lat]))
    });
    this.markerSource.addFeature(feature);
  }
  
}
