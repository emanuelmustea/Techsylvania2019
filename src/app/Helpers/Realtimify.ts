import { Observable } from 'rxjs';

const Realtimify = (httpFunction ) =>{
    const CFunction = (observer)=>{
        return httpFunction().subscribe((value)=>{
            observer.next(value);
        });
    }
    return new Observable(obs=>{
        CFunction(obs);
        setInterval(()=>CFunction(obs), 2000)
    })
}
export default Realtimify;