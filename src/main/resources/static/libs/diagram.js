var diagramm = {
      htmlElement: document.getElementById("diagram"),
      servants:[],
      nextPosition: 0,
      relationsMargin: 35,
      servantMargin: 20,

      adjustLineForServant: function(servant) {
        var line = this.createElement("div.vertical-line", null, null, this.htmlElement);
        line.style.height = this.linesHeight + "px";
        this.placeElement(line, servant.domElement.offsetHeight,
                          servant.domElement.offsetWidth/2 + servant.domElement.offsetLeft)
        servant.linePosition = line.offsetLeft;
        servant.lineDomElement = line;
        return line;
      },

      placeRelationName: function(relation) {
        var relNameContainer = relation.signalNameBox;
        var contWidth = relNameContainer.offsetWidth;
        var top = relation.top - 15;
        var left = relation.left + relation.domElement.clientWidth/2 - contWidth/2;
        this.placeElement(relNameContainer, top, left);
      },

      createElement: function(selector, innerHtml, namespace, appendTo) {
        var selectorParts = selector.split(/\.|#/);
        var identifiers = selector.split(/[a-zA-z0-9_-]{1,}/);
        var element;
        if(namespace){
          element = document.createElementNS(namespace, selectorParts[0]);
        } else {
          element = document.createElement(selectorParts[0])
        }
        if(innerHtml){
          element.innerHTML = innerHtml;
        }
        for(var i=1; i< selectorParts.length; i++){
          var ident = identifiers[i];
          if(ident == "."){
            element.classList.add(selectorParts[i]);
          } else if(ident == "#"){
            element.id = selectorParts[i];
          }
        }
        if(appendTo){
          appendTo.appendChild(element);
        }
        return element;
      },

      placeElement: function(element, top, left, model) {
        element.style.top = top + "px";
        element.style.left = left + "px";
        if(model) {
          model.top = top;
          model.left = left;
        }
      },

      drawDiagramm: function(inputData) {
        var servants = inputData.servants;
        var relations = inputData.relations;
        var placedServants = [];
        var nextLeftPosition = 0;
        var nextTopPosition = 65;

        for(var servIndex = 0; servIndex < servants.length; servIndex++){
          var servant = servants[servIndex];
          servant.index = servIndex;
          var servNameContainer = this.createElement("div.servant-name-box", servant.name, null, this.htmlElement);
          servant.domElement = servNameContainer;
          for(var placedServindex = 0; placedServindex < placedServants.length; placedServindex++){
            var placedServant = placedServants[placedServindex];
            var signals = this.getSignalsBetwenServants(servant, placedServant, inputData.relations);
            for(var sinalIndex = 0; sinalIndex < signals.length; sinalIndex++) {
              var signalNameBox = this
              .createElement("div.relation-name-container", signals[sinalIndex].name, null, this.htmlElement);
              signals[sinalIndex].signalNameBox = signalNameBox;
              this.updateNexPosIfNeed(signals[sinalIndex]);
            }
          }
          this.placeElement(servNameContainer, 0, this.nextPosition, servant);
          placedServants.push(servant);
          this.nextPosition = servant.domElement.offsetLeft + servant.domElement.offsetWidth +
            this.servantMargin;
        }
        for(relIndex = 0; relIndex < inputData.relations.length; relIndex++){
          this.placeRelation(inputData.relations[relIndex]);
        }
        this.linesHeight = inputData.relations.length*35 + 35;
        for(var placedServindex = 0; placedServindex < placedServants.length; placedServindex++){
          var servant = placedServants[placedServindex];
          this.adjustLineForServant(servant);
          var servNameContainer = this.createElement("div.servant-name-box", servant.name, null, this.htmlElement);
          this.placeElement(servNameContainer, this.linesHeight + servant.domElement.offsetHeight, servant.left);
        }
      },

      placeRelation: function(signal) {
        var fromServant = signal.from == signal.servantA.name ? signal.servantA : signal.servantB;
        var toServant = signal.to == signal.servantA.name ? signal.servantA : signal.servantB;
        var svgContainer = this.createElement("svg.relation", null, "http://www.w3.org/2000/svg");
        var line = this.createElement("polyline", null, "http://www.w3.org/2000/svg");
        var signalName = signal.signalNameBox;
        var positionRelation = fromServant.index < toServant.index;
        var width = Math.abs(this.getServantCenter(fromServant) - this.getServantCenter(toServant));
        svgContainer.appendChild(line);
        svgContainer.style.width = width + "px";
        var top = signal.index * this.relationsMargin + 35;
        var left = signal.servantA.left + signal.servantA.domElement.offsetWidth/2;
        this.placeElement(svgContainer, top, left, signal);
        this.nexVerticalPosition += (8 + this.relationsMargin);
        signal.domElement = svgContainer;
        if(positionRelation){
          line.setAttribute("points", "0,4 " + width + ",4 " + (width - 10) +",0 " + width + ",4 " + (width - 10) +",8");
          svgContainer.setAttribute("direction", "ltr");
        } else {
          line.setAttribute("points", "10,0 0,4 10,8 0,4 " + width + ",4");
          svgContainer.setAttribute("direction", "rtl");
        }
        this.htmlElement.appendChild(svgContainer);
        this.placeRelationName(signal);
        this.placeIcons(signal);
      },

      getServantCenter: function(servant) {
       return servant.left + servant.domElement.offsetWidth/2;
      },

      updateNexPosIfNeed: function(signal) {
        var signalNameWidth = signal.signalNameBox.offsetWidth;
        var leftStart = signal.servantA.left + signal.servantA.domElement.offsetWidth/2;
        var assumeLeftEnd = this.nextPosition + signal.servantB.domElement.offsetWidth/2;
        var assumeSignalWidth = signalNameWidth + 20;
        if(assumeLeftEnd >=(leftStart + assumeSignalWidth)) {
          return;
        } else {
          this.nextPosition = leftStart + assumeSignalWidth - signal.servantB.domElement.offsetWidth/2;
        }
      },

       getSignalsBetwenServants: function(servant, placedServant, signals) {
        var signalsBetwenServants = [];

        signals.forEach(function(signal, index){
          if((servant.name == signal.from && placedServant.name == signal.to)||
            (servant.name == signal.to && placedServant.name == signal.from)){
            signal.servantA = placedServant;
            signal.servantB = servant;
            signal.index = index + 1;
            signalsBetwenServants.push(signal);
          }
        });
         return signalsBetwenServants;
       },

      placeIcons: function(signal) {
        if(signal.error){
          var signalDirection = signal.domElement.getAttribute("direction");
          var top = signal.top;
          var left = 0;
          if(signalDirection == "rtl"){
            left = signal.left + 10;
          } else {
            left = signal.left + signal.domElement.clientWidth - 15;
          }
          var iconsTop = top + signal.domElement.clientHeight;
          var iconDomElement = this.createElement("span.icon.error-icon.glyphicon.glyphicon-exclamation-sign",null, null, this.htmlElement);
          this.placeElement(iconDomElement, iconsTop, left);
        }
      },

    }
