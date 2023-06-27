export default function HP({data}) {
    return (
        <div>
            {
                data.map((d, index) => {
                    if (index == 0)
                        return <div>Hero: {d.hp}</div>
                    else if (index != data.length - 1)
                        return <div>Goblin: {d.hp}</div>
                })
            }
            
        </div>
    )
}