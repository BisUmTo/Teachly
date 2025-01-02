document.addEventListener("DOMContentLoaded", () => {
    // BLOCKLY
    (async () => {
        const blocklyDiv = document.getElementById('blocklyDiv');
        const blocklyArea = document.getElementById('blocklyArea');
        const state = JSON.parse(blocklyDiv.innerHTML);
        blocklyDiv.innerHTML = '';
        const workspace = Blockly.inject(blocklyDiv, {
            readOnly: true,
            scrollbars: true,
        });
        Blockly.serialization.workspaces.load(state, workspace);

        const onresize = function(e) {
            let element = blocklyArea;
            let x = element.offsetLeft;
            let y = element.offsetTop;
            blocklyDiv.style.left = x + 'px';
            blocklyDiv.style.top = y + 'px';
            blocklyDiv.style.width = blocklyArea.offsetWidth + 'px';
            blocklyDiv.style.height = blocklyArea.offsetHeight + 'px';
            Blockly.svgResize(workspace);
        };
        window.addEventListener('resize', onresize, false);
        onresize();

        const blocklyCard = $('#blocklyCard');
        if (blocklyCard.length) {
            const resizeObserver = new ResizeObserver(entries => {
                entries.forEach(entry => onresize());
            });
            resizeObserver.observe(blocklyCard[0]);
        }
    })();

});