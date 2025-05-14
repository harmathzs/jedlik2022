import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { Link } from 'react-router-dom';
import '../../Bootstrap UI/bootstrap.min.css';
import './App.css';
import './openpage.css';

export default function Openpage() {
    return(
        <div className="container">
            <div className="start w-100">
                <h1 className="text-center pt-2 pt-lg-4">Á.L.B. Ingatlanügynöség</h1>
                <div className="row">
                    <div className="col-12 col-sm-6 text-center">
                        <Link className="btn btn-primary" to="/offers">Nézze meg kínálatunkat!</Link>
                    </div>
                    <div className="col-12 col-sm-6 text-center">
                        <a className="btn btn-primary" href="#">Hirdessen nálunk!</a>
                    </div>
                </div>
            </div>
        </div>
    );
}
