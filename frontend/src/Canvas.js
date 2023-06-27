import { useEffect, useRef, useState } from "react";

export default function Canvas({keyListener, data}) {
    const canvasRef = useRef(null);
    const [map, setMap] = useState(new Image(800, 800))

    useEffect(() => {
        const canvas =  canvasRef.current;
        const context = canvas.getContext('2d');

        context.fillStyle = '#123456';
        context.fillRect(0, 0, context.canvas.width, context.canvas.height);

        if (data.length !== 0) {
        // hero and map in the array
            if (data[0].hp == 0) {
                context.font = "48px serif";
                context.fillStyle = 'black';
                context.fillText("DEFEAT", 250, 400);
            } else if (data.length === 2) {
                context.font = "48px serif";
                context.fillStyle = 'black';
                context.fillText("VICTORY", 250, 400);
            } else {
                    data.reverse().map((d, index) => {
                    const img = new Image();
                    img.src = `data:image/png;base64,${d.image}`;

                    let offesetX = img.width/2;
                    let offesetY = img.height/2;

                    if (index === 0) {
                        offesetX = 0;
                        offesetY = 0;
                        setMap(img);
                    }

                    context.drawImage(
                        img, 
                        d.position[0] - offesetX,
                        d.position[1] - offesetY
                    );
            });
        }
    }}, [data])

    return <canvas tabIndex='0' onKeyDown={e => keyListener(e)} 
                ref={canvasRef} width={map.width + 20} height={map.height + 20} />
}