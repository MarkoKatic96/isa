import React, { Component } from 'react'
import axios from 'axios'
import { withRouter} from 'react-router-dom';
import ReservationsChooseBar from './ReservationsChooseBar';

class MyReservations extends Component{

    state={
        rezervacije:[]
    }

    componentDidMount() {
        axios.get("http://localhost:8080/rezervacija/user/3")
        .then(res=>{
            this.setState({
                rezervacije: res.data
            })
            console.log(res.data);
        })
    }

    render(){
        var {rezervacije}=this.state;
        const rezervacijeList = rezervacije.length ? (rezervacije.map(rezervacija => {
            return(
                <div className="center container" key={rezervacija.id}>
                    <div className="row">
                        <div className="col s12 m12">
                            <div className="card grey darken-2 card-panel hoverable">
                                <div className="card-content white-text left-align">
                                <span className="card-title"><b>{rezervacija.datumOd} - {rezervacija.datumDo}</b></span>
                                <div className="divider white"></div>
                                <br/>
                                <p>Cena: {rezervacija.ukupnaCena}</p>
                                </div>
                                <div className="divider white"></div>
                                <div className="card-action">
                                    <button className="btn waves-effect waves-light green" id="sobeBtn"/* onClick={()=>{this.sobeClick(hotel.id)}}*/>Sobe</button>
                                    <button className="btn waves-effect waves-light green" id="uslugeBtn" /*onClick={()=>{this.uslugeClick(hotel.id)}}*/>Dodatne usluge</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            )
        })):(
            <div className="center">Nije pronadjena nijedana rezervacija.</div>
        )

        return(
            <div className="center container">
                <br/>
                <ReservationsChooseBar/>
                <br/>
                <h3 className="left-align container">Rezervacije:</h3>
                <br/>
                {rezervacijeList}
            </div>
        )
        
    }

}
export default withRouter(MyReservations)