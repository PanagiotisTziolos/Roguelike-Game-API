import { useEffect, useState } from 'react';
import Canvas from './Canvas.js';
import HP from './HP.js';
import './App.css';

function App() {
    // primitive functionality for constant fetching of data
    // GAME LOOP
    const [data, setData] = useState([]);

    useEffect(() => {
        fetch('/play');
        const interval = setInterval(() => {
            fetch('/play')
                .then(res => res.json())
                .then(data => setData(data));
        }, 70);
        return () => clearInterval(interval);
    }, []);

    const handleInput = async (e) => {
        await fetch('/play', {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/json'
                        },
                        body: JSON.stringify(e.key)
                    });
    };
    
    const handleRestart = async () => {
        await fetch('/restart');
    }

    return(
        <div>
            <Canvas
                keyListener={handleInput}
                data={data}
            />
            <HP data={data}/>
            <div>
                <button onClick={handleRestart}>Restart</button>
            </div>
        </div>
    )
}

export default App;
