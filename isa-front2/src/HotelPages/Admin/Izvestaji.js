import React, { Component } from 'react'
import axios from 'axios'
import { withRouter} from 'react-router-dom';
import {
    VictoryBar,
    VictoryChart,
    VictoryLine,
    VictoryPie,
  } from "victory";

class Izvestaji extends Component{

    state={
        oceneBtn: true,
        prihodiBtn: false,
        posecenostBtn: false,
        sobe:[],
        prosekHotela:""
    }

    componentDidMount(){
        var token = localStorage.getItem('jwtToken');
        var a = "";
        axios.get("http://localhost:8080/korisnik/all/" + localStorage.getItem('email'))
        .then(res=>{
            this.setState({
                korisnik: res.data
            })
            console.log(res.data);
            a = res.data.zaduzenZaId;
            axios.get("http://localhost:8080/sobe/all/" + a)
            .then(res=>{
                this.setState({
                    sobe: res.data
                })
                console.log(res.data);
                axios.get("http://localhost:8080/ocena/prosek/hotel/" + a)
                .then(res=>{
                    this.setState({
                        prosekHotela: res.data
                    })
                    console.log(res.data);
                })
            })
        })

    }

    render(){
        return(
            <div className="container center">
                <button className="btn waves-effect waves-light red darken-3" id="btnOcene1" onClick={this.clickAvio}>Ocene</button>
                <button className="btn waves-effect waves-light red darken-3" id="btnOcene2" onClick={this.clickHotel}>Prihodi</button>
                <button className="btn waves-effect waves-light red darken-3" id="btnOcene3" onClick={this.clickCar}>Posecenost</button>
                <div className="container center">
                    <br/>
                    <h5 className="left">Prosecna ocena hotela: {this.state.prosekHotela}</h5>
                    <br/>
                    <h5 className="left">Prosecne ocene soba:</h5>
                    <VictoryChart>
                        <VictoryBar
                            data={[
                            { reptile: 'lizard', awesomeness: 1234 },
                            { reptile: 'snake', awesomeness: 2048 },
                            { reptile: 'crocodile', awesomeness: 2600 },
                            { reptile: 'alligator', awesomeness: 9000 },
                            ]}
                            x="reptile"
                            y="awesomeness"
                        />
                    </VictoryChart>
                </div>
            </div>
        )
    }

}
export default withRouter(Izvestaji)