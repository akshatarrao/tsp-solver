import { Component, OnInit } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css'],
})
export class NavbarComponent implements OnInit {
  readonly languages = [
    new Language(
      'en',
      'English',
      'https://upload.wikimedia.org/wikipedia/en/a/ae/Flag_of_the_United_Kingdom.svg'
    ),
  ];
  currentLanguage = window.navigator.language == 'fr' ? 'fr' : 'en';

  constructor(public translateService: TranslateService) {
    translateService.addLangs(this.languages.map((value) => value.code));
    translateService.setDefaultLang(this.currentLanguage);
  }

  ngOnInit(): void {
    this.currentLanguage =
      this.translateService.currentLang != null
        ? this.translateService.currentLang
        : this.translateService.defaultLang;
    this.translateService.use(this.currentLanguage);
  }

  changeLanguage(language: string) {
    this.currentLanguage = language;
    this.translateService.use(language);
  }
}

class Language {
  code: string;
  name: string;
  flagIconUrl: string;

  constructor(code: string, name: string, flagIconUrl: string) {
    this.code = code;
    this.name = name;
    this.flagIconUrl = flagIconUrl;
  }
}
