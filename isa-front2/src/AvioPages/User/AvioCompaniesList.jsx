import React, { Component } from 'react';
import axios from 'axios';
import { Link, withRouter } from 'react-router-dom';

class AvioCompaniesList extends Component {
    state = {
        kompanija: "",
        avgrating: "",
        sveAviokompanije: [],
        destinacijeNaKojimaPosluje: []
    }

    componentDidMount() {

        axios.get('http://localhost:8080/aviocompany/getall').then(
            res => {
                this.setState({
                    sveAviokompanije: res.data
                })
            })

        // axios.get('http://localhost:8080/aviocompany/getone/' + this.props.match.params.flightid).then(
        //     res => {
        //         this.setState({
        //             kompanija: res.data
        //         })

        //         let kompanija = this.state.kompanija.idAvioKompanije;
        //         axios.get('http://localhost:8080/destination/getalldestsbycompany/' + kompanija).then(
        //             res => {
        //                 console.log("DESTINACIJE: ")
        //                 console.log(res.data)
        //                 this.setState({
        //                     destinacijeNaKojimaPosluje: res.data
        //                 })
        //             }


        //         )
        //     }


        // )
        //za prosecnu ocenu
        // axios.get('http://localhost:8080/aviocompany/getavgrating/' + this.props.match.params.flightid).then(
        //     res => {
        //         console.log(res.data);
        //         this.setState({
        //             avgrating: res.data
        //         })
        //     }
        // )
    }

    sortCompanies = (e) =>
    {
        axios.get('http://localhost:8080/aviocompany/sort/' + e.target.value).then(
            res => {
                this.setState({
                    sveAviokompanije: res.data
                })
                this.props.history.push('/allcompanieslist');
            })
    }

    render() {
        // const destinacijeNaKojimaPosluje = this.state.destinacijeNaKojimaPosluje.length ? (this.state.destinacijeNaKojimaPosluje.map(dest => {
        //     return (
        //         <div key={dest.idDestinacije}>
        //             <div><i>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{dest.naziv}</i></div>
        //         </div>
        //     );
        // })) : (
        //         <h3>Nema destinacija</h3>
        //     )

            const letovi = (this.state.sveAviokompanije.length) ? (this.state.sveAviokompanije.map(kompanija => {
                return(
                <div>
                <div className="center container">
                    <div className="row" key={kompanija.idAvioKompanije}>
                        <div className="col s12 m12">
                            <div className="card grey darken-2 card-panel hoverable">
                                <div className="card-content white-text left-align">
                                    <span className="card-title"><b>Naziv aviokompanije: {kompanija.naziv}</b></span>
                                    <div className="divider white"></div>
                                    <br />
                                    <p>Naziv: &nbsp;&nbsp; {kompanija.naziv}</p>
                                    <p>Adresa: &nbsp;&nbsp;{kompanija.adresa}</p>
                                    <p>Opis: &nbsp;&nbsp;{kompanija.opis}</p>
                                    <p>Destinacije na kojima posluje: &nbsp;&nbsp;</p> {/*{destinacijeNaKojimaPosluje}*/}
                                </div>
                                <div className="divider white"></div>
                                <div className="card-action">
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            )})) : (<div>Nema aviokompanija za prikaz</div>)

        return (
            <div>
                <br />
            <Link to="/companies"><button className="btn red center lighten-1 z-depth-0">Nazad</button></Link><br /><br />
            Sortiraj po:
            <select id="sort" className="browser-default" name="sort" onChange = {(e) => {this.sortCompanies(e)}}>
                                <option value="1">Naziv</option>
                                <option value="2">Grad</option>
                            </select>



                {letovi}
            </div>
        );
    }
}

export default withRouter(AvioCompaniesList);