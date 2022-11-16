import { Injectable } from "@angular/core";

@Injectable()
export class Util {
    shDateToJSDate(shDate: string): Date {
        let jsdate: Date;
        jsdate = new Date();
        const token = shDate.split("-");
        jsdate.setDate(+token[2]);
        jsdate.setMonth((+token[1])-1),
        jsdate.setFullYear(+token[0]);
        return jsdate;
    }

    jsDateToSHDate(date: Date): string {
        let shDate = date.getFullYear() + '-';
        shDate += (date.getMonth() < 10) ? ('0' + date.getMonth()) : date.getMonth() + '';    
        shDate += '-';
        shDate += (date.getDate() < 10) ? ('0' + date.getDate()) : date.getDate() + '';
        return shDate;
    }
}