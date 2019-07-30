import React, { Component } from 'react'
import axios from 'axios'
import { withRouter} from 'react-router-dom';
import ChooseBar from '../ChooseBar';

class Hotels extends Component{

    state={
        hoteli:[],
        nazivAdresa:"",
        datumOd:"",
        datumDo:"",
        brojOsoba:"",
        brojSoba:""
    }

    componentDidMount() {
        axios.get("http://localhost:8080/hotel/all")
        .then(res=>{
            this.setState({
                hoteli: res.data
            })
        })
    }

    handleChange = (e) => { //za inpute
        this.setState({
            [e.target.id]: e.target.value
        })
    }

    handleSubmit = (e) => {
        e.preventDefault();
            axios.post("http://localhost:8080/pretraga/", {nazivAdresa: this.state.nazivAdresa, datumOd: this.state.datumOd, datumDo: this.state.datumDo, brojGostiju: this.state.brojOsoba, brojSoba: this.state.brojSoba})
            .then(res => {
                this.setState({
                    hoteli: res.data
                })
            })
    }

    sobeClick=(hotelId)=>{
        this.props.history.push("/rooms/" + hotelId);
    }

    uslugeClick=(hotelId)=>{
        this.props.history.push("/services/" + hotelId);
    }

    render(){
        var {hoteli}=this.state;
        const hoteliList = hoteli.length ? (hoteli.map(hotel => {
            return(
                <div className="center container" key={hotel.id}>
                    <div className="row">
                        <div className="col s12 m12">
                            <div className="card grey darken-2 card-panel hoverable">
                                <div className="card-content white-text left-align">
                                <span className="card-title"><b>{hotel.naziv}</b></span>
                                <div className="divider white"></div>
                                <br/>
                                <p>Adresa: {hotel.adresa}</p>
                                <p>Opis: {hotel.opis}</p>
                                <p>Ocena: {hotel.ocena}</p>
                                </div>
                                <div className="divider white"></div>
                                <div className="card-action">
                                    <button className="btn waves-effect waves-light green" id="sobeBtn" onClick={()=>{this.sobeClick(hotel.id)}}>Sobe</button>
                                    <button className="btn waves-effect waves-light green" id="uslugeBtn" onClick={()=>{this.uslugeClick(hotel.id)}}>Dodatne usluge</button>
                                    {this.props.loggedIn ? (
                                        <button className="btn waves-effect waves-light green" id="rezervisiBtn" onClick={()=>{this.uslugeClick(hotel.id)}}>Rezrvisi</button>
                                    ):(
                                        <p/>
                                    )}
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            )
        })):(
            <div className="center">Nije pronadjen nijedan hotel.</div>
        )

        return(
            <div className="center container">
                <br/>
                <ChooseBar/>
                <br/>
                <div className="center container">
                    <form onSubmit={this.handleSubmit}>
                        <label className="left black-text" htmlFor="nazivAdresa">Naziv/Adresa:</label>
                        <input type="text" id="nazivAdresa" onChange={this.handleChange}/>
                        <label className="left black-text" htmlFor="datumOd">Datum od:</label>
                        <input type="date" id="datumOd" onChange={this.handleChange}/>
                        <label className="left black-text" htmlFor="datumDo">Datum do:</label>
                        <input type="date" id="datumDo" onChange={this.handleChange}/>
                        <label className="left black-text" htmlFor="brojOsoba">Broj osoba:</label>
                        <input type="number" id="brojOsoba" onChange={this.handleChange}/>
                        <label className="left black-text" htmlFor="brojSoba">Broj soba:</label>
                        <input type="number" id="brojSoba" onChange={this.handleChange}/>
                        <button className="btn waves-effect waves-light green">Pretrazi</button>
                    </form>
                </div>
                <br/>
                <h3 className="left-align container">Hoteli:</h3>
                <br/>
                {hoteliList}
            </div>
        )
        
    }

}
export default withRouter(Hotels)