import { Injectable } from "@angular/core";

@Injectable()
export class UtilDate {
    private readonly daysOfWeek: string[];
    private readonly monthOfYear: string[];

    constructor() {
        this.daysOfWeek = ["dimanche", "lundi", "mardi", "mercredi", "jeudi", "vendredi", "samedi"]
        this.monthOfYear = ["janv", "fevr", "mars", "avri", "mai", "juin", "juil", "août", "sept", "octo", "nov", "dec"];
    }

    public getJSDate(date: string): Date {
        let jsdate: Date;
        jsdate = new Date();
        const token = date.split("-");
        jsdate.setDate(+token[2]);
        jsdate.setMonth((+token[1])-1),
        jsdate.setFullYear(+token[0]);
        return jsdate;
    }

    getMonth(date: string): string {
        return this.monthOfYear[this.getJSDate(date).getMonth()];
    }

    getDay(date: string): string {
        return this.daysOfWeek[this.getJSDate(date).getDay()];
    }

    getFormattedTime(time: string): string {
        const token = time.split(':');
        const hour = ((+token[0])<10 ? '0' + token[0] : token[0]);
        const minites = ((+token[1])<10 ? '0' + token[1] : token[1]);
        return hour + ':' + minites;
    }
}