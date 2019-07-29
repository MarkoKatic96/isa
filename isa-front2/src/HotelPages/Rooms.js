import React, { Component } from 'react'
import axios from 'axios'
import { withRouter} from 'react-router-dom';

class Rooms extends Component{

    state={
        sobe:[]
    }

    componentDidMount() {
        var id = this.props.match.params.hotelId;
        axios.get("http://localhost:8080/sobe/all/" + id)
        .then(res=>{
            this.setState({
                sobe: res.data
            })
        })
    }

    render(){
        var {sobe}=this.state;
        const sobeList = sobe.length ? (sobe.map(soba => {
            return(
                <div className="center container" key={soba.id}>
                    <div className="row">
                        <div className="col s12 m12">
                            <div className="card grey darken-2 card-panel hoverable">
                                <div className="card-content white-text left-align">
                                <span className="card-title"><b>{soba.brojSobe}</b></span>
                                <div className="divider white"></div>
                                <br/>
                                <p>Sprat: {soba.sprat}</p>
                                <p>Broj kreveta: {soba.brojKreveta}</p>
                                <p>Tip sobe: {soba.tipSobe.naziv}</p>
                                <p>Ocena: {soba.ocena}</p>
                                <p>Cena nocenja: {soba.cenaNocenja}</p>
                                </div>
                                <div className="divider white"></div>
                                <div className="card-action">
                                    <p>Potencijlni dugmici</p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            )
        })):(
            <div className="center">Nije pronadjena nijedna soba.</div>
        )

        return(
            <div className="center container">
                <br/>
                <h3 className="left-align container">Sobe:</h3>
                <br/>
                {sobeList}
            </div>
        )
    }

}
export default withRouter(Rooms)